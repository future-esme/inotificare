import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITemplate } from '../template.model';
import { TemplateService } from '../service/template.service';
import { TemplateFormService, TemplateFormGroup } from './template-form.service';

@Component({
  standalone: true,
  selector: 'jhi-template-update',
  templateUrl: './template-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TemplateUpdateComponent implements OnInit {
  isSaving = false;
  template: ITemplate | null = null;

  editForm: TemplateFormGroup = this.templateFormService.createTemplateFormGroup();

  constructor(
    protected templateService: TemplateService,
    protected templateFormService: TemplateFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ template }) => {
      this.template = template;
      if (template) {
        this.updateForm(template);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const template = this.templateFormService.getTemplate(this.editForm);
    if (template.id !== null) {
      this.subscribeToSaveResponse(this.templateService.update(template));
    } else {
      this.subscribeToSaveResponse(this.templateService.create(template));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITemplate>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(template: ITemplate): void {
    this.template = template;
    this.templateFormService.resetForm(this.editForm, template);
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITemplate } from 'app/entities/template/template.model';
import { TemplateService } from 'app/entities/template/service/template.service';
import { Channel } from 'app/entities/enumerations/channel.model';
import { RecipientType } from 'app/entities/enumerations/recipient-type.model';
import { Priority } from 'app/entities/enumerations/priority.model';
import { MessageStatus } from 'app/entities/enumerations/message-status.model';
import { RequestDataService } from '../service/request-data.service';
import { IRequestData } from '../request-data.model';
import { RequestDataFormService, RequestDataFormGroup } from './request-data-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-data-update',
  templateUrl: './request-data-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestDataUpdateComponent implements OnInit {
  isSaving = false;
  requestData: IRequestData | null = null;
  channelValues = Object.keys(Channel);
  recipientTypeValues = Object.keys(RecipientType);
  priorityValues = Object.keys(Priority);
  messageStatusValues = Object.keys(MessageStatus);

  templateIdsCollection: ITemplate[] = [];

  editForm: RequestDataFormGroup = this.requestDataFormService.createRequestDataFormGroup();

  constructor(
    protected requestDataService: RequestDataService,
    protected requestDataFormService: RequestDataFormService,
    protected templateService: TemplateService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareTemplate = (o1: ITemplate | null, o2: ITemplate | null): boolean => this.templateService.compareTemplate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestData }) => {
      this.requestData = requestData;
      if (requestData) {
        this.updateForm(requestData);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestData = this.requestDataFormService.getRequestData(this.editForm);
    if (requestData.id !== null) {
      this.subscribeToSaveResponse(this.requestDataService.update(requestData));
    } else {
      this.subscribeToSaveResponse(this.requestDataService.create(requestData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestData>>): void {
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

  protected updateForm(requestData: IRequestData): void {
    this.requestData = requestData;
    this.requestDataFormService.resetForm(this.editForm, requestData);

    this.templateIdsCollection = this.templateService.addTemplateToCollectionIfMissing<ITemplate>(
      this.templateIdsCollection,
      requestData.templateId,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.templateService
      .query({ 'requestDataId.specified': 'false' })
      .pipe(map((res: HttpResponse<ITemplate[]>) => res.body ?? []))
      .pipe(
        map((templates: ITemplate[]) =>
          this.templateService.addTemplateToCollectionIfMissing<ITemplate>(templates, this.requestData?.templateId),
        ),
      )
      .subscribe((templates: ITemplate[]) => (this.templateIdsCollection = templates));
  }
}

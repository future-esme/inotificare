import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITemplate, NewTemplate } from '../template.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITemplate for edit and NewTemplateFormGroupInput for create.
 */
type TemplateFormGroupInput = ITemplate | PartialWithRequiredKeyOf<NewTemplate>;

type TemplateFormDefaults = Pick<NewTemplate, 'id'>;

type TemplateFormGroupContent = {
  id: FormControl<ITemplate['id'] | NewTemplate['id']>;
  title: FormControl<ITemplate['title']>;
  bodyRo: FormControl<ITemplate['bodyRo']>;
  bodyRu: FormControl<ITemplate['bodyRu']>;
  bodyEn: FormControl<ITemplate['bodyEn']>;
  bodyShortRo: FormControl<ITemplate['bodyShortRo']>;
  bodyShortRu: FormControl<ITemplate['bodyShortRu']>;
  bodyShortEn: FormControl<ITemplate['bodyShortEn']>;
  subjectRo: FormControl<ITemplate['subjectRo']>;
  subjectRu: FormControl<ITemplate['subjectRu']>;
  subjectEn: FormControl<ITemplate['subjectEn']>;
};

export type TemplateFormGroup = FormGroup<TemplateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TemplateFormService {
  createTemplateFormGroup(template: TemplateFormGroupInput = { id: null }): TemplateFormGroup {
    const templateRawValue = {
      ...this.getFormDefaults(),
      ...template,
    };
    return new FormGroup<TemplateFormGroupContent>({
      id: new FormControl(
        { value: templateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(templateRawValue.title, {
        validators: [Validators.required],
      }),
      bodyRo: new FormControl(templateRawValue.bodyRo),
      bodyRu: new FormControl(templateRawValue.bodyRu),
      bodyEn: new FormControl(templateRawValue.bodyEn),
      bodyShortRo: new FormControl(templateRawValue.bodyShortRo),
      bodyShortRu: new FormControl(templateRawValue.bodyShortRu),
      bodyShortEn: new FormControl(templateRawValue.bodyShortEn),
      subjectRo: new FormControl(templateRawValue.subjectRo),
      subjectRu: new FormControl(templateRawValue.subjectRu),
      subjectEn: new FormControl(templateRawValue.subjectEn),
    });
  }

  getTemplate(form: TemplateFormGroup): ITemplate | NewTemplate {
    return form.getRawValue() as ITemplate | NewTemplate;
  }

  resetForm(form: TemplateFormGroup, template: TemplateFormGroupInput): void {
    const templateRawValue = { ...this.getFormDefaults(), ...template };
    form.reset(
      {
        ...templateRawValue,
        id: { value: templateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TemplateFormDefaults {
    return {
      id: null,
    };
  }
}

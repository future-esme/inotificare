import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TemplateService } from '../service/template.service';
import { ITemplate } from '../template.model';
import { TemplateFormService } from './template-form.service';

import { TemplateUpdateComponent } from './template-update.component';

describe('Template Management Update Component', () => {
  let comp: TemplateUpdateComponent;
  let fixture: ComponentFixture<TemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let templateFormService: TemplateFormService;
  let templateService: TemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TemplateUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    templateFormService = TestBed.inject(TemplateFormService);
    templateService = TestBed.inject(TemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const template: ITemplate = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ template });
      comp.ngOnInit();

      expect(comp.template).toEqual(template);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITemplate>>();
      const template = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(templateFormService, 'getTemplate').mockReturnValue(template);
      jest.spyOn(templateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ template });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: template }));
      saveSubject.complete();

      // THEN
      expect(templateFormService.getTemplate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(templateService.update).toHaveBeenCalledWith(expect.objectContaining(template));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITemplate>>();
      const template = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(templateFormService, 'getTemplate').mockReturnValue({ id: null });
      jest.spyOn(templateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ template: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: template }));
      saveSubject.complete();

      // THEN
      expect(templateFormService.getTemplate).toHaveBeenCalled();
      expect(templateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITemplate>>();
      const template = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(templateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ template });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(templateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

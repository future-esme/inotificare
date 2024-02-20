import { ITemplate, NewTemplate } from './template.model';

export const sampleWithRequiredData: ITemplate = {
  id: 'e2f4e039-31e2-494c-abad-bfeb3701ef6e',
  title: 'escape',
};

export const sampleWithPartialData: ITemplate = {
  id: 'b4919afe-f934-4062-8e1d-365802f517c3',
  title: 'altruistic',
  bodyRo: 'given now',
  bodyRu: 'cement keenly modulo',
  bodyEn: 'psst',
  bodyShortRo: 'hungrily hmph',
  subjectRo: 'cyst yum',
  subjectRu: 'suspiciously before toward',
  subjectEn: 'tinderbox incidentally week',
};

export const sampleWithFullData: ITemplate = {
  id: '111efd05-4f37-47e2-901d-1a5b71b1acb1',
  title: 'for prance',
  bodyRo: 'near',
  bodyRu: 'gratefully yahoo',
  bodyEn: 'terribly motion geez',
  bodyShortRo: 'phooey',
  bodyShortRu: 'medical',
  bodyShortEn: 'ease eek',
  subjectRo: 'grumpy parse',
  subjectRu: 'despite that aha',
  subjectEn: 'wherever quizzically',
};

export const sampleWithNewData: NewTemplate = {
  title: 'psst',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

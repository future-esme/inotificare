import { IRequestData } from 'app/entities/request-data/request-data.model';

export interface ITemplate {
  id: string;
  title?: string | null;
  bodyRo?: string | null;
  bodyRu?: string | null;
  bodyEn?: string | null;
  bodyShortRo?: string | null;
  bodyShortRu?: string | null;
  bodyShortEn?: string | null;
  subjectRo?: string | null;
  subjectRu?: string | null;
  subjectEn?: string | null;
  requestData?: IRequestData | null;
}

export type NewTemplate = Omit<ITemplate, 'id'> & { id: null };

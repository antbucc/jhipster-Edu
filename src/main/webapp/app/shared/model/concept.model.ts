import { ICompetence } from 'app/shared/model/competence.model';

export interface IConcept {
  id?: string;
  title?: string | null;
  competences?: ICompetence[] | null;
}

export const defaultValue: Readonly<IConcept> = {};

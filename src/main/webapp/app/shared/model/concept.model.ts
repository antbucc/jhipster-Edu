import { ICompetence } from 'app/shared/model/competence.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IConcept {
  id?: string;
  title?: string | null;
  description?: string | null;
  competences?: ICompetence[] | null;
  activities?: IActivity[] | null;
}

export const defaultValue: Readonly<IConcept> = {};

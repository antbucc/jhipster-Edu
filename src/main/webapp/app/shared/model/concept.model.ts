import { IGoal } from 'app/shared/model/goal.model';
import { ICompetence } from 'app/shared/model/competence.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IConcept {
  id?: string;
  title?: string | null;
  description?: string | null;
  goals?: IGoal[] | null;
  competences?: ICompetence[] | null;
  activities?: IActivity[] | null;
}

export const defaultValue: Readonly<IConcept> = {};

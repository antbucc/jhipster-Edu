import { ICompetence } from 'app/shared/model/competence.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IGoal } from 'app/shared/model/goal.model';

export interface IConcept {
  id?: string;
  title?: string | null;
  description?: string | null;
  competences?: ICompetence[] | null;
  activities?: IActivity[] | null;
  goals?: IGoal[] | null;
}

export const defaultValue: Readonly<IConcept> = {};

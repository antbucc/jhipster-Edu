import { IConcept } from 'app/shared/model/concept.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IPrecondition {
  id?: string;
  metadata?: string | null;
  concepts?: IConcept[] | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IPrecondition> = {};

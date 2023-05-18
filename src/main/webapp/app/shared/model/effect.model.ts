import { IConcept } from 'app/shared/model/concept.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IEffect {
  id?: string;
  metadata?: string | null;
  concepts?: IConcept[] | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IEffect> = {};

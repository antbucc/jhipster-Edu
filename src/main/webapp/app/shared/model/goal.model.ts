import { IConcept } from 'app/shared/model/concept.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface IGoal {
  id?: string;
  title?: string | null;
  concepts?: IConcept[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<IGoal> = {};

import { IFragment } from 'app/shared/model/fragment.model';
import { IConcept } from 'app/shared/model/concept.model';

export interface IGoal {
  id?: string;
  title?: string | null;
  fragments?: IFragment[] | null;
  concepts?: IConcept[] | null;
}

export const defaultValue: Readonly<IGoal> = {};

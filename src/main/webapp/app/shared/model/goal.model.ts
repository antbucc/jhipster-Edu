import { IFragment } from 'app/shared/model/fragment.model';

export interface IGoal {
  id?: string;
  title?: string | null;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<IGoal> = {};

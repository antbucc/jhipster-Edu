import { IFragment } from 'app/shared/model/fragment.model';

export interface IPrecondition {
  id?: string;
  title?: string | null;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<IPrecondition> = {};

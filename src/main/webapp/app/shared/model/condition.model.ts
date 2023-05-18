import { IFragment } from 'app/shared/model/fragment.model';
import { ConditionType } from 'app/shared/model/enumerations/condition-type.model';

export interface ICondition {
  id?: string;
  title?: string | null;
  type?: ConditionType | null;
  targetFragment?: IFragment | null;
  sourceFragment?: IFragment | null;
}

export const defaultValue: Readonly<ICondition> = {};

import { IFragment } from 'app/shared/model/fragment.model';
import { ConditionType } from 'app/shared/model/enumerations/condition-type.model';

export interface ICondition {
  id?: string;
  description?: string | null;
  type?: ConditionType | null;
  types?: IFragment[] | null;
}

export const defaultValue: Readonly<ICondition> = {};

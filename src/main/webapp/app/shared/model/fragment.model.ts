import { IActivity } from 'app/shared/model/activity.model';
import { ICondition } from 'app/shared/model/condition.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  previous?: IFragment[] | null;
  activities?: IActivity[] | null;
  next?: IFragment | null;
  source?: ICondition | null;
  module?: IModule | null;
}

export const defaultValue: Readonly<IFragment> = {};

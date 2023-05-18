import { IPrecondition } from 'app/shared/model/precondition.model';
import { IEffect } from 'app/shared/model/effect.model';
import { IGoal } from 'app/shared/model/goal.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  condition?: IFragment | null;
  parents?: IFragment[] | null;
  preconditions?: IPrecondition[] | null;
  effects?: IEffect[] | null;
  goals?: IGoal[] | null;
  activities?: IActivity[] | null;
  children?: IFragment | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IFragment> = {};

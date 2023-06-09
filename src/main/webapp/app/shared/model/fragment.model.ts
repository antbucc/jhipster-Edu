import { IPrecondition } from 'app/shared/model/precondition.model';
import { IEffect } from 'app/shared/model/effect.model';
import { IPath } from 'app/shared/model/path.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IGoal } from 'app/shared/model/goal.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  preconditions?: IPrecondition[] | null;
  effects?: IEffect[] | null;
  outgoingPaths?: IPath[] | null;
  activities?: IActivity[] | null;
  goals?: IGoal[] | null;
}

export const defaultValue: Readonly<IFragment> = {};

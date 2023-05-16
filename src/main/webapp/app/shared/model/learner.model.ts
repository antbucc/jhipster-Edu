import { IScenario } from 'app/shared/model/scenario.model';

export interface ILearner {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  scenarios?: IScenario[] | null;
}

export const defaultValue: Readonly<ILearner> = {};

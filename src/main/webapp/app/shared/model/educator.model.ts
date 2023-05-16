import { IScenario } from 'app/shared/model/scenario.model';

export interface IEducator {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  scenarios?: IScenario[] | null;
}

export const defaultValue: Readonly<IEducator> = {};

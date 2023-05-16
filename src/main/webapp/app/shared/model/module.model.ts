import dayjs from 'dayjs';
import { IScenario } from 'app/shared/model/scenario.model';
import { Level } from 'app/shared/model/enumerations/level.model';

export interface IModule {
  id?: string;
  title?: string | null;
  description?: string | null;
  startDate?: string | null;
  endData?: string | null;
  level?: Level | null;
  scenario?: IScenario | null;
}

export const defaultValue: Readonly<IModule> = {};

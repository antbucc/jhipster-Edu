import { IScenario } from 'app/shared/model/scenario.model';

export interface IDomain {
  id?: string;
  title?: string | null;
  description?: string | null;
  city?: string | null;
  scenario?: IScenario | null;
}

export const defaultValue: Readonly<IDomain> = {};

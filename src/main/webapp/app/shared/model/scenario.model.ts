import { IModule } from 'app/shared/model/module.model';
import { IDomain } from 'app/shared/model/domain.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IScenario {
  id?: string;
  title?: string | null;
  description?: string | null;
  language?: Language | null;
  module?: IModule | null;
  domains?: IDomain[] | null;
}

export const defaultValue: Readonly<IScenario> = {};

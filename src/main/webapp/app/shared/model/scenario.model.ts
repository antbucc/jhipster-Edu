import { IDomain } from 'app/shared/model/domain.model';
import { ILearner } from 'app/shared/model/learner.model';
import { IModule } from 'app/shared/model/module.model';
import { IEducator } from 'app/shared/model/educator.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IScenario {
  id?: string;
  title?: string | null;
  description?: string | null;
  language?: Language | null;
  domain?: IDomain | null;
  learners?: ILearner[] | null;
  module?: IModule | null;
  educators?: IEducator[] | null;
}

export const defaultValue: Readonly<IScenario> = {};

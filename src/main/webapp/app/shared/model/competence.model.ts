import { IConcept } from 'app/shared/model/concept.model';
import { IScenario } from 'app/shared/model/scenario.model';
import { CompetenceType } from 'app/shared/model/enumerations/competence-type.model';

export interface ICompetence {
  id?: string;
  title?: string | null;
  description?: string | null;
  type?: CompetenceType | null;
  concepts?: IConcept[] | null;
  competences?: ICompetence[] | null;
  scenarios?: IScenario[] | null;
  competences?: ICompetence[] | null;
}

export const defaultValue: Readonly<ICompetence> = {};

import { IConcept } from 'app/shared/model/concept.model';
import { IFragment } from 'app/shared/model/fragment.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';
import { Tool } from 'app/shared/model/enumerations/tool.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IActivity {
  id?: string;
  title?: string | null;
  description?: string | null;
  type?: ActivityType | null;
  tool?: Tool | null;
  difficulty?: Difficulty | null;
  concepts?: IConcept[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<IActivity> = {};

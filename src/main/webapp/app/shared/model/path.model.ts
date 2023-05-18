import { IFragment } from 'app/shared/model/fragment.model';
import { IModule } from 'app/shared/model/module.model';
import { PathType } from 'app/shared/model/enumerations/path-type.model';

export interface IPath {
  id?: string;
  title?: string | null;
  type?: PathType | null;
  targetFragment?: IFragment | null;
  sourceFragment?: IFragment | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IPath> = {};

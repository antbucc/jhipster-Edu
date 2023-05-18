import { IModule } from 'app/shared/model/module.model';

export interface IPath {
  id?: string;
  title?: string | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IPath> = {};

export interface IEducator {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
}

export const defaultValue: Readonly<IEducator> = {};

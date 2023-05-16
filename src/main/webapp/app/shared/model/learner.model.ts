export interface ILearner {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
}

export const defaultValue: Readonly<ILearner> = {};

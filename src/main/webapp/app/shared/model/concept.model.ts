export interface IConcept {
  id?: string;
  title?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IConcept> = {};

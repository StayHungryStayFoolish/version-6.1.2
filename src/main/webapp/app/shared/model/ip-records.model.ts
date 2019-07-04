export interface IIPRecords {
  id?: number;
  userId?: number;
  device?: string;
  ipAddress?: string;
  countryName?: string;
  cityName?: string;
  status?: string;
}

export const defaultValue: Readonly<IIPRecords> = {};

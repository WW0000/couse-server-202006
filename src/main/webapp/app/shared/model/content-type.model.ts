import { Moment } from 'moment';

export interface IContentType {
  id?: number;
  contentTypeName?: string;
  contentTypeSort?: number;
  contentTypeTime?: Moment;
  contentTypeUpdateCount?: number;
}

export class ContentType implements IContentType {
  constructor(
    public id?: number,
    public contentTypeName?: string,
    public contentTypeSort?: number,
    public contentTypeTime?: Moment,
    public contentTypeUpdateCount?: number
  ) {}
}

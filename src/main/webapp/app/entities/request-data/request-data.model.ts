import dayjs from 'dayjs/esm';
import { ITemplate } from 'app/entities/template/template.model';
import { INotification } from 'app/entities/notification/notification.model';
import { Channel } from 'app/entities/enumerations/channel.model';
import { RecipientType } from 'app/entities/enumerations/recipient-type.model';
import { Priority } from 'app/entities/enumerations/priority.model';
import { MessageStatus } from 'app/entities/enumerations/message-status.model';

export interface IRequestData {
  id: string;
  channel?: keyof typeof Channel | null;
  recipients?: string | null;
  recipientType?: keyof typeof RecipientType | null;
  priority?: keyof typeof Priority | null;
  content?: string | null;
  messageStatus?: keyof typeof MessageStatus | null;
  createdTime?: dayjs.Dayjs | null;
  templateId?: ITemplate | null;
  notification?: INotification | null;
}

export type NewRequestData = Omit<IRequestData, 'id'> & { id: null };

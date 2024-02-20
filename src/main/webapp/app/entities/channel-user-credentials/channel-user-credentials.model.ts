import { INotification } from 'app/entities/notification/notification.model';
import { INotifySettings } from 'app/entities/notify-settings/notify-settings.model';
import { Channel } from 'app/entities/enumerations/channel.model';

export interface IChannelUserCredentials {
  id: string;
  chatId?: string | null;
  channel?: keyof typeof Channel | null;
  notification?: INotification | null;
  notifySettings?: INotifySettings | null;
}

export type NewChannelUserCredentials = Omit<IChannelUserCredentials, 'id'> & { id: null };

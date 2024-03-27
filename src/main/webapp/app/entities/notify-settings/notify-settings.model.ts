import { Channel } from '../enumerations/channel.model';
import { NotifyChannelStatusEnum } from '../enumerations/notify-channel-status-enum.model';
import dayjs from 'dayjs/esm';

export interface INotifySettings {
  id: string;
  channel: Channel;
  status: NotifyChannelStatusEnum;
  channelsToken?: ChannelToken;
}

export class ChannelToken {
  constructor(
    public token: string,
    public createdTime: dayjs.Dayjs,
    public expirationTime: dayjs.Dayjs,
    public isHidden: boolean,
  ) {}
}

export * from './book.service';
import { BookService } from './book.service';
export * from './discover.service';
import { DiscoverService } from './discover.service';
export const APIS = [BookService, DiscoverService];

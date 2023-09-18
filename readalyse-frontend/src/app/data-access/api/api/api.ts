export * from './agent.service';
import { AgentService } from './agent.service';
export * from './authentication.service';
import { AuthenticationService } from './authentication.service';
export * from './book.service';
import { BookService } from './book.service';
export * from './discover.service';
import { DiscoverService } from './discover.service';
export * from './library.service';
import { LibraryService } from './library.service';
export * from './review.service';
import { ReviewService } from './review.service';
export const APIS = [AgentService, AuthenticationService, BookService, DiscoverService, LibraryService, ReviewService];

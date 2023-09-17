export * from './agent.service';
import { AgentService } from './agent.service';
export * from './book.service';
import { BookService } from './book.service';
export * from './discover.service';
import { DiscoverService } from './discover.service';
export * from './review.service';
import { ReviewService } from './review.service';
export const APIS = [AgentService, BookService, DiscoverService, ReviewService];

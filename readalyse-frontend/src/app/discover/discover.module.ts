import {NgModule} from "@angular/core";
import {DiscoverComponent} from "./discover.component";
import {RouterModule, Routes} from "@angular/router";
import {SharedComponentsModule} from "../shared/shared-components.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { DiscoverFullComponent } from './discover-full/discover-full.component';

const routes: Routes = [
  {
    path: '', component: DiscoverComponent
  },
  {
    path: ':listName', component: DiscoverFullComponent
  }
  ];
@NgModule({
  imports: [RouterModule.forChild(routes), SharedComponentsModule, FontAwesomeModule],
  declarations: [DiscoverComponent, DiscoverFullComponent],
  exports: [],
  providers: []
})

export class DiscoverModule {}

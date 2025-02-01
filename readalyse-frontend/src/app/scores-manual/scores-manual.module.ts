import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {SharedComponentsModule} from "../shared/shared-components.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ScoresManualComponent} from "./scores-manual.component";

const routes: Routes = [
  {path: '', component: ScoresManualComponent},

];
@NgModule({
  imports: [RouterModule.forChild(routes), SharedComponentsModule, FontAwesomeModule],
  declarations: [ScoresManualComponent],
  exports: [],
  providers: []
})

export class ScoresManualModule {}

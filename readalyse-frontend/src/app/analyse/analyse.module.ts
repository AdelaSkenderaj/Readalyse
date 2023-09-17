import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AnalyseComponent} from "./analyse.component";

const routes: Routes = [{path: '', component: AnalyseComponent}]
@NgModule({
  imports: [RouterModule.forChild(routes)],
  declarations: [AnalyseComponent],
  exports: [],
  providers: []
})

export class AnalyseModule {}

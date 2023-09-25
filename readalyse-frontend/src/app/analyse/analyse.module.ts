import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AnalyseComponent} from "./analyse.component";
import {FormsModule} from "@angular/forms";

const routes: Routes = [{path: '', component: AnalyseComponent}]
@NgModule({
  imports: [RouterModule.forChild(routes), FormsModule],
  declarations: [AnalyseComponent],
  exports: [],
  providers: []
})

export class AnalyseModule {}

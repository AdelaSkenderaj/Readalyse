import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {SharedComponentsModule} from "../shared/shared-components.module";
import {CategoryComponent} from "./category.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

const routes: Routes = [{path: '', component: CategoryComponent}];
@NgModule({
  imports: [RouterModule.forChild(routes), SharedComponentsModule, FontAwesomeModule],
  declarations: [CategoryComponent],
  exports: [],
  providers: []
})

export class CategoryModule {}

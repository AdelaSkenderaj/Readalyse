import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {FavoriteComponent} from "./favorite.component";
import {SharedComponentsModule} from "../shared/shared-components.module";

const routes: Routes = [
  {
    path: '', component: FavoriteComponent
  }
]
@NgModule({
    imports: [RouterModule.forChild(routes), SharedComponentsModule],
  declarations: [FavoriteComponent],
  exports: [],
  providers: []
})

export class FavoriteModule {}

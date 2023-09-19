import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {SharedComponentsModule} from "../shared/shared-components.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {MyLibraryComponent} from "./my-library.component";
import {BookInformationComponent} from "../shared/book-information/book-information.component";

const routes: Routes = [
  {path: '', component: MyLibraryComponent},
  {path: ':bookId', component: BookInformationComponent},

];
@NgModule({
  imports: [RouterModule.forChild(routes), SharedComponentsModule, FontAwesomeModule],
  declarations: [MyLibraryComponent],
  exports: [],
  providers: []
})

export class MyLibraryModule {}

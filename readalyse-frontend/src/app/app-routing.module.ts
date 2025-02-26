import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {NavigationComponent} from "./navigation/navigation.component";
import {LoginComponent} from "./login/login.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {SignupComponent} from "./register/signup.component";
import {BookInformationComponent} from "./shared/book-information/book-information.component";

const routes: Routes = [
  {
    path: '',
    component: NavigationComponent,
    canActivate: [AuthenticationGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'discover'
      },
      {
        path: 'book/:bookId',
        component: BookInformationComponent
      },
      {
        path: 'discover',
        loadChildren: () =>
          import('./discover/discover.module').then(
            (m) => m.DiscoverModule
          ),
      },
      {
        path: 'category',
        loadChildren: () =>
          import('./category/category.module').then(
            (m) => m.CategoryModule
          )
      },
      {
        path: 'library',
        loadChildren: () =>
          import('./my-library/my-library.module').then(
            (m) => m.MyLibraryModule
          )
      },
      {
        path: 'analyse',
        loadChildren: () =>
          import('./analyse/analyse.module').then(
            (m) => m.AnalyseModule
          )
      },
      {
        path: 'favorite',
        loadChildren: () =>
          import('./favorite/favorite.module').then(
            (m) => m.FavoriteModule
          )
      },
      {
        path: 'scores-manual',
        loadChildren: () =>
          import('./scores-manual/scores-manual.module').then(
            (m) => m.ScoresManualModule
          )
      }
    ],
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: SignupComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

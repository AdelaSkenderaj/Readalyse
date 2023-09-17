import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {NavigationComponent} from "./navigation/navigation.component";

const routes: Routes = [
  {
    path: '',
    component: NavigationComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'discover'
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
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

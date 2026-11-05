
import { Routes } from '@angular/router';

// Claimant components
import { Dashboard as ClaimantDashboard } from './claimant/dashboard/dashboard';
import { SubmitClaim } from './claimant/submit-claim/submit-claim';
import { ClaimList } from './claimant/claim-list/claim-list';
import { ClaimDetails } from './claimant/claim-details/claim-details';

// Claims officer components
import { Dashboard as OfficerDashboard } from './officer/dashboard/dashboard';
import { IncomingClaims } from './officer/incoming-claims/incoming-claims';
import { ClaimReview } from './officer/claim-review/claim-review';

export const routes: Routes = [

  // Default page
  {
    path: '',
    redirectTo: 'claimant/dashboard',
    pathMatch: 'full'
  },

  // Claimant routes
  {
    path: 'claimant/dashboard',
    component: ClaimantDashboard
  },
  {
    path: 'claimant/submit-claim',
    component: SubmitClaim
  },
  {
    path: 'claimant/claim-list',
    component: ClaimList
  },
  {
    path: 'claimant/claim-details/:claimNumber',
    component: ClaimDetails
  },

  // Claims officer routes
  {
    path: 'officer/dashboard',
    component: OfficerDashboard
  },
  {
    path: 'officer/incoming-claims',
    component: IncomingClaims
  },
  {
    path: 'officer/claim-review/:claimNumber',
    component: ClaimReview
  },

  // Invalid routes
  {
    path: '**',
    redirectTo: 'claimant/dashboard'
  }

];

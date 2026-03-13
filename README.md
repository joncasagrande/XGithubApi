# Android Exercise

## XGithub API

#### Your mission: Build a small Android app that lets people browse public Github repos and dive into the details of any repo that catches their eye.

### Core Features
1. **List repositories** from GitHub API:
   - Endpoint: `https://api.github.com/orgs/xing/repos`
   - When an item is clicked, navigate to its detail screen
   - For each item, display:
     - repository name
     - description (if null, show a fallback)
     - owner login
     - light green background if `fork == true`, otherwise white
2. **Repository detail screen:**
   - On item tap, navigate to details
   - Display the following info:
     - name
     - description
     - stars
     - forks
     - language
     - owner info
3. **States & UX**
   - Loading indicator
   - Empty state
   - Error state with retry
   - Content descriptions for images (accessibility)

  
- Have you implemented all the features?
- [ ] Yes
- [X] No


- Have you used AI? 
- [X] Yes
- [ ] No

- PS -> AI features are in PR yet.

### References:
- [GitHub API](https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user)

- [Ktor Testing](https://ktor.io/docs/client-testing.html)

- [Ktor Testing Api](https://akjaw.com/using-ktor-client-mock-engine-for-integration-and-ui-tests/)

- [Splash Screen](https://dev.to/elozino/getting-started-with-splash-screen-in-jetpack-compose-144l)

- [Pull to refresh](https://developer.android.com/develop/ui/compose/components/pull-to-refresh)

- [Search Bar](https://developer.android.com/develop/ui/compose/components/search-bar)


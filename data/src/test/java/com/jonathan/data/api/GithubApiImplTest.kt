package com.jonathan.data.api

import com.jonathan.data.model.Github
import com.jonathan.data.utils.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.gson.gson
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GithubApiImplTest {

    @MockK
    lateinit var client: HttpClient

    lateinit var githubApi: GithubApi

    @Test
    fun ClientSuccessTest() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = content,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            client = HttpClient(
                engine = mockEngine
            ) {
                install(ContentNegotiation) {
                    gson {
                        setPrettyPrinting()
                        disableHtmlEscaping()
                    }
                }
            }
            githubApi = GithubApiImpl(client)
           val result =  githubApi.fetchRepos(null)
            assertEquals(true, (result as NetworkResult.Success<List<Github>>).body.isNotEmpty())
        }
    }

    @Test
    fun Client404Test() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = content,
                    status = HttpStatusCode.NotFound,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            client = HttpClient(
                engine = mockEngine
            ) {
                install(ContentNegotiation) {
                    gson {
                        setPrettyPrinting()
                        disableHtmlEscaping()
                    }
                }
            }
            githubApi = GithubApiImpl(client)
            val result =  githubApi.fetchRepos(null)
            assertEquals("Repo not found.", (result as NetworkResult.Error<List<Github>>).error.message)
        }
    }

    @Test
    fun Client500Test() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = content,
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            client = HttpClient(
                engine = mockEngine
            ) {
                install(ContentNegotiation) {
                    gson {
                        setPrettyPrinting()
                        disableHtmlEscaping()
                    }
                }
            }
            githubApi = GithubApiImpl(client)
            val result =  githubApi.fetchRepos(null)
            assertEquals("Server Disruption! We are on fixing it.", (result as NetworkResult.Error<List<Github>>).error.message)
        }
    }


    val content = " [\n" +
            "  {\n" +
            "    \"id\": 234107,\n" +
            "    \"node_id\": \"MDEwOlJlcG9zaXRvcnkyMzQxMDc=\",\n" +
            "    \"name\": \"gearman-ruby\",\n" +
            "    \"full_name\": \"xing/gearman-ruby\",\n" +
            "    \"private\": false,\n" +
            "    \"owner\": {\n" +
            "      \"login\": \"xing\",\n" +
            "      \"id\": 27901,\n" +
            "      \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjI3OTAx\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/27901?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/xing\",\n" +
            "      \"html_url\": \"https://github.com/xing\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/xing/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/xing/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/xing/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/xing/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/xing/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/xing/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/xing/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/xing/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/xing/received_events\",\n" +
            "      \"type\": \"Organization\",\n" +
            "      \"user_view_type\": \"public\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"html_url\": \"https://github.com/xing/gearman-ruby\",\n" +
            "    \"description\": \"Ruby library for the Gearman distributed job system\",\n" +
            "    \"fork\": true,\n" +
            "    \"url\": \"https://api.github.com/repos/xing/gearman-ruby\",\n" +
            "    \"forks_url\": \"https://api.github.com/repos/xing/gearman-ruby/forks\",\n" +
            "    \"keys_url\": \"https://api.github.com/repos/xing/gearman-ruby/keys{/key_id}\",\n" +
            "    \"collaborators_url\": \"https://api.github.com/repos/xing/gearman-ruby/collaborators{/collaborator}\",\n" +
            "    \"teams_url\": \"https://api.github.com/repos/xing/gearman-ruby/teams\",\n" +
            "    \"hooks_url\": \"https://api.github.com/repos/xing/gearman-ruby/hooks\",\n" +
            "    \"issue_events_url\": \"https://api.github.com/repos/xing/gearman-ruby/issues/events{/number}\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/xing/gearman-ruby/events\",\n" +
            "    \"assignees_url\": \"https://api.github.com/repos/xing/gearman-ruby/assignees{/user}\",\n" +
            "    \"branches_url\": \"https://api.github.com/repos/xing/gearman-ruby/branches{/branch}\",\n" +
            "    \"tags_url\": \"https://api.github.com/repos/xing/gearman-ruby/tags\",\n" +
            "    \"blobs_url\": \"https://api.github.com/repos/xing/gearman-ruby/git/blobs{/sha}\",\n" +
            "    \"git_tags_url\": \"https://api.github.com/repos/xing/gearman-ruby/git/tags{/sha}\",\n" +
            "    \"git_refs_url\": \"https://api.github.com/repos/xing/gearman-ruby/git/refs{/sha}\",\n" +
            "    \"trees_url\": \"https://api.github.com/repos/xing/gearman-ruby/git/trees{/sha}\",\n" +
            "    \"statuses_url\": \"https://api.github.com/repos/xing/gearman-ruby/statuses/{sha}\",\n" +
            "    \"languages_url\": \"https://api.github.com/repos/xing/gearman-ruby/languages\",\n" +
            "    \"stargazers_url\": \"https://api.github.com/repos/xing/gearman-ruby/stargazers\",\n" +
            "    \"contributors_url\": \"https://api.github.com/repos/xing/gearman-ruby/contributors\",\n" +
            "    \"subscribers_url\": \"https://api.github.com/repos/xing/gearman-ruby/subscribers\",\n" +
            "    \"subscription_url\": \"https://api.github.com/repos/xing/gearman-ruby/subscription\",\n" +
            "    \"commits_url\": \"https://api.github.com/repos/xing/gearman-ruby/commits{/sha}\",\n" +
            "    \"git_commits_url\": \"https://api.github.com/repos/xing/gearman-ruby/git/commits{/sha}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/xing/gearman-ruby/comments{/number}\",\n" +
            "    \"issue_comment_url\": \"https://api.github.com/repos/xing/gearman-ruby/issues/comments{/number}\",\n" +
            "    \"contents_url\": \"https://api.github.com/repos/xing/gearman-ruby/contents/{+path}\",\n" +
            "    \"compare_url\": \"https://api.github.com/repos/xing/gearman-ruby/compare/{base}...{head}\",\n" +
            "    \"merges_url\": \"https://api.github.com/repos/xing/gearman-ruby/merges\",\n" +
            "    \"archive_url\": \"https://api.github.com/repos/xing/gearman-ruby/{archive_format}{/ref}\",\n" +
            "    \"downloads_url\": \"https://api.github.com/repos/xing/gearman-ruby/downloads\",\n" +
            "    \"issues_url\": \"https://api.github.com/repos/xing/gearman-ruby/issues{/number}\",\n" +
            "    \"pulls_url\": \"https://api.github.com/repos/xing/gearman-ruby/pulls{/number}\",\n" +
            "    \"milestones_url\": \"https://api.github.com/repos/xing/gearman-ruby/milestones{/number}\",\n" +
            "    \"notifications_url\": \"https://api.github.com/repos/xing/gearman-ruby/notifications{?since,all,participating}\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/xing/gearman-ruby/labels{/name}\",\n" +
            "    \"releases_url\": \"https://api.github.com/repos/xing/gearman-ruby/releases{/id}\",\n" +
            "    \"deployments_url\": \"https://api.github.com/repos/xing/gearman-ruby/deployments\",\n" +
            "    \"created_at\": \"2009-06-23T08:02:08Z\",\n" +
            "    \"updated_at\": \"2023-01-28T18:51:29Z\",\n" +
            "    \"pushed_at\": \"2013-08-07T03:19:40Z\",\n" +
            "    \"git_url\": \"git://github.com/xing/gearman-ruby.git\",\n" +
            "    \"ssh_url\": \"git@github.com:xing/gearman-ruby.git\",\n" +
            "    \"clone_url\": \"https://github.com/xing/gearman-ruby.git\",\n" +
            "    \"svn_url\": \"https://github.com/xing/gearman-ruby\",\n" +
            "    \"homepage\": \"http://gearman.org/\",\n" +
            "    \"size\": 280,\n" +
            "    \"stargazers_count\": 8,\n" +
            "    \"watchers_count\": 8,\n" +
            "    \"language\": \"Ruby\",\n" +
            "    \"has_issues\": true,\n" +
            "    \"has_projects\": true,\n" +
            "    \"has_wiki\": true,\n" +
            "    \"has_pages\": false,\n" +
            "    \"has_discussions\": false,\n" +
            "    \"forks_count\": 1,\n" +
            "    \"mirror_url\": null,\n" +
            "    \"archived\": true,\n" +
            "    \"disabled\": false,\n" +
            "    \"open_issues_count\": 0,\n" +
            "    \"license\": {\n" +
            "      \"key\": \"mit\",\n" +
            "      \"name\": \"MIT License\",\n" +
            "      \"spdx_id\": \"MIT\",\n" +
            "      \"url\": \"https://api.github.com/licenses/mit\",\n" +
            "      \"node_id\": \"MDc6TGljZW5zZTEz\"\n" +
            "    },\n" +
            "    \"allow_forking\": true,\n" +
            "    \"is_template\": false,\n" +
            "    \"web_commit_signoff_required\": false,\n" +
            "    \"has_pull_requests\": true,\n" +
            "    \"pull_request_creation_policy\": \"all\",\n" +
            "    \"topics\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"visibility\": \"public\",\n" +
            "    \"forks\": 1,\n" +
            "    \"open_issues\": 0,\n" +
            "    \"watchers\": 8,\n" +
            "    \"default_branch\": \"master\",\n" +
            "    \"permissions\": {\n" +
            "      \"admin\": false,\n" +
            "      \"maintain\": false,\n" +
            "      \"push\": false,\n" +
            "      \"triage\": false,\n" +
            "      \"pull\": true\n" +
            "    },\n" +
            "    \"custom_properties\": {\n" +
            "\n" +
            "    }\n" +
            "  }" +
            "]"
}
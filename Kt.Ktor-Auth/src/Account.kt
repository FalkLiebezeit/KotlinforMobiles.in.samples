package info.kofler

import org.mindrot.jbcrypt.BCrypt

data class Account(val account: String,
                   val fullname: String,
                   val email: String,
                   val hash: String) {

    fun validate(pw: String): Boolean {
        return BCrypt.checkpw(pw, hash)
    }

}

fun setupTestAccounts() : MutableList<Account> {
    val accounts = mutableListOf<Account>()
    accounts += Account("mkofler",
        "Michael Kofler",
        "michael.kofler@myhost.com",
        BCrypt.hashpw("geheim", BCrypt.gensalt()))
    accounts += Account("account2",
        "Maria Maier",
        "mariam@myhost.com",
        BCrypt.hashpw("magicpw", BCrypt.gensalt()))
    return accounts
}

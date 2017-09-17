package matsu_chara.quill.free.repo

import io.getquill.{MysqlJdbcContext, SnakeCase}
import matsu_chara.quill.free.Person

class PersonRepository() {

  def insert(p: Person)(implicit ctx: MysqlJdbcContext[SnakeCase]): Unit = {
    import ctx._
    val q = quote {
      query[Person].insert(lift(p))
    }
    run(q)
    ()
  }

  def findById(id: Long)(implicit ctx: MysqlJdbcContext[SnakeCase]): Option[Person] = {
    import ctx._
    val q = quote {
      query[Person].filter(_.id == lift(id))
    }
    run(q).headOption
  }

  def deleteAll()(implicit ctx: MysqlJdbcContext[SnakeCase]): Unit = {
    import ctx._
    val q = quote {
      query[Person].delete
    }
    run(q)
    ()
  }
}

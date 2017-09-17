package matsu_chara.quill.free.repo

import io.getquill.monad.Effect
import io.getquill.{MysqlJdbcContext, SnakeCase}
import matsu_chara.quill.free.Person

class PersonFreeRepository {

  def insert(p: Person)(implicit ctx: MysqlJdbcContext[SnakeCase]): ctx.IO[Unit, Effect.Write] = {
    import ctx._
    val q = quote {
      query[Person].insert(lift(p))
    }
    runIO(q).map(_ => ())
  }

  def findById(id: Long)(implicit ctx: MysqlJdbcContext[SnakeCase]): ctx.IO[Option[Person], Effect.Read] = {
    import ctx._
    val q = quote {
      query[Person].filter(_.id == lift(id))
    }
    runIO(q).map(_.headOption)
  }

  def deleteAll()(implicit ctx: MysqlJdbcContext[SnakeCase]): ctx.IO[Unit, Effect.Write] = {
    import ctx._
    val q = quote {
      query[Person].delete
    }
    runIO(q).map(_ => ())
  }
}
